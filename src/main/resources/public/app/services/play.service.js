onmessage = function(e) {

    var response;
    if (e.data['one'] == e.data['two']) {
        setTimeout(function(){
            response = e.data['one'];
            postMessage(response);
        }, 0);
    } else {
        setTimeout(function(){
            response = e.data['one'];
            postMessage(response);
        }, e.data['two'][2]);
    }
}
