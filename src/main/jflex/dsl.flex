package seng302;

import java_cup.runtime.*;

%%

%class DslLexer


%line
%column
%unicode

%cupsym DslSymbol
%cup
   
/* Declarations
 * The contents of this block is copied verbatim into the Lexer class, this
 * provides the ability to create member variable and methods to use in the
 * action blocks for rules.
*/
%{   
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/* Macro Declarations
 * See: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
*/
WhiteSpace = \p{Whitespace}
PlusMinus = [+|-]{1}
Word = [a-zA-Z0-9#]*
Character = [a-zA-Z]{1}
Number = [-]?[0-9]+
FlatSharpNumber = ([-]?[0-9]+)([#|b]?)
Punctuation = \p{Punct}
TestNumber = x([1-9]{1})([0-9]*)

SimpleNote = [a-gA-G]{1}[#|b]?([-]?[0-9]+)?
FullNote = ([a-gA-G]){1}(x|b{1,2}|#{1,2})?(-?[0-9]+)?

%%

/* Rules */
<YYINITIAL> {
    "version"               { return symbol(DslSymbol.COMMAND_VERSION); }
    "midi"                  { return symbol(DslSymbol.COMMAND_MIDI); }
    "note"                  { return symbol(DslSymbol.COMMAND_NOTE); }
    "semitone"              { return symbol(DslSymbol.COMMAND_SEMITONE); }
    "hasEnharmonic"         { return symbol(DslSymbol.COMMAND_HASENHARMONIC); }
    "simpleEnharmonic"      { return symbol(DslSymbol.COMMAND_SIMPLEENHARMONIC); }
    "enharmonic"            { return symbol(DslSymbol.COMMAND_ENHARMONIC); }
    "play"                  { return symbol(DslSymbol.COMMAND_PLAYNOTE); }
    "pitchTutor"            { return symbol(DslSymbol.COMMAND_PITCHTUTOR); }
    "intervalTutor"         { return symbol(DslSymbol.COMMAND_INTERVALTUTOR); }
    "scaleTutor"            { return symbol(DslSymbol.COMMAND_SCALETUTOR); }
    "getTempo"              { return symbol(DslSymbol.COMMAND_GETTEMPO); }
    "setTempo"              { return symbol(DslSymbol.COMMAND_SETTEMPO); }
    "getCrotchet"           { return symbol(DslSymbol.COMMAND_GETCROTCHET); }
    "interval"              { return symbol(DslSymbol.COMMAND_INTERVAL); }
    "semitonesInterval"     { return symbol(DslSymbol.COMMAND_SEMITONESINTERVAL); }
    "findInterval"          { return symbol(DslSymbol.COMMAND_FINDINTERVAL); }
    "hasEnharmonicInterval" { return symbol(DslSymbol.COMMAND_HASENHARMONICINTERVAL); }
    "enharmonicInterval"    { return symbol(DslSymbol.COMMAND_ENHARMONICINTERVAL); }
    "chord"                 { return symbol(DslSymbol.COMMAND_CHORD); }
    "findChord"             { return symbol(DslSymbol.COMMAND_FINDCHORD); }
    "termDefine"            { return symbol(DslSymbol.COMMAND_TERMDEFINE); }
    "termMeaning"           { return symbol(DslSymbol.COMMAND_TERMMEANING); }
    "termLanguage"          { return symbol(DslSymbol.COMMAND_TERMLANGUAGE); }
    "termCategory"          { return symbol(DslSymbol.COMMAND_TERMCATEGORY); }
    "termLookup"            { return symbol(DslSymbol.COMMAND_TERMLOOKUP); }
    "termRemove"            { return symbol(DslSymbol.COMMAND_TERMREMOVE); }
    "playScale"             { return symbol(DslSymbol.COMMAND_PLAYSCALE); }
    "playInterval"          { return symbol(DslSymbol.COMMAND_PLAYINTERVAL); }
    "playChord"             { return symbol(DslSymbol.COMMAND_PLAYCHORD); }
    "findScale"             { return symbol(DslSymbol.COMMAND_FINDSCALE); }
    "findScaleMidi"         { return symbol(DslSymbol.COMMAND_FINDSCALEMIDI); }
    "termTutor"             { return symbol(DslSymbol.COMMAND_TERMTUTOR); }
    "chordTutor"            { return symbol(DslSymbol.COMMAND_CHORDTUTOR); }
    "signatureTutor"        { return symbol(DslSymbol.COMMAND_SIGNATURETUTOR); }
    "keyboard"              { return symbol(DslSymbol.COMMAND_KEYBOARD); }
    "scaleSignature"        { return symbol(DslSymbol.COMMAND_SCALESIGNATURE); }
    "signature"             { return symbol(DslSymbol.COMMAND_SIGNATURE); }
    "quality"               { return symbol(DslSymbol.COMMAND_QUALITY); }
    "chordFunction"         { return symbol(DslSymbol.COMMAND_CHORDFUNCTION); }
    "function"              { return symbol(DslSymbol.COMMAND_FUNCTION); }
    "spellingTutor"         { return symbol(DslSymbol.COMMAND_CHORDSPELLINGTUTOR); }
    "mode"                  { return symbol(DslSymbol.COMMAND_MODE); }
    "parent"                { return symbol(DslSymbol.COMMAND_PARENT); }
    "help"                  { return symbol(DslSymbol.COMMAND_HELP); }

    "("                 { return symbol(DslSymbol.OPEN_PAR); }
    ")"                 { return symbol(DslSymbol.CLOSE_PAR); }
    ","                 { return symbol(DslSymbol.COMMA); }
    "\""                { return symbol(DslSymbol.QUOTE); }

    {WhiteSpace}        { /* Ignore whitespace */ }
    {PlusMinus}         { return symbol(DslSymbol.PLUS_MINUS, yytext()); }
    {Word}              { return symbol(DslSymbol.WORD, yytext()); }
    {Character}         { return symbol(DslSymbol.CHARACTER, yytext()); }
    {Number}            { return symbol(DslSymbol.NUMBER, new Integer(yytext())); }
    {FlatSharpNumber}   { return symbol(DslSymbol.FLAT_SHARP_NUMBER, yytext()); }
    {Punctuation}       { return symbol(DslSymbol.PUNCTUATION, yytext()); }
    {TestNumber}        { return symbol(DslSymbol.TEST_NUMBER, yytext()); }

    {SimpleNote}        { return symbol(DslSymbol.SIMPLE_NOTE, yytext()); }
    {FullNote}          { return symbol(DslSymbol.FULL_NOTE, yytext()); }
}

/* Throw an exception if we have no matches */
[^]                     { throw new RuntimeException("Illegal character <"+yytext()+">"); }