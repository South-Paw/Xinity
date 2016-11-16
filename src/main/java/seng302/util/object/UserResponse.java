package seng302.util.object;

/**
 * A user profile object.
 * Object wrapper for a users public details.
 *
 * @author adg62
 */
public class UserResponse {

    private Integer userId;
    private String username;
    private String name;
    private String email;
    private String location;
    private String bio;
    private String imgUrl;
    private String theme;
    private Integer tempo;

    /**
     * Create a new user profile response.
     *
     * @param userId The users id.
     * @param username The username.
     * @param name The real name.
     * @param email The users email.
     * @param location The users location.
     * @param bio The users bio text.
     * @param imgUrl Their profile image url.
     * @param theme The users theme.
     * @param tempo The users tempo.
     */
    public UserResponse(Integer userId, String username, String name, String email, String location,
                        String bio, String imgUrl, String theme, Integer tempo) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.location = location;
        this.bio = bio;
        this.imgUrl = imgUrl;
        this.theme = theme;
        this.tempo = tempo;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getBio() {
        return bio;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTheme() {
        return theme;
    }

    public Integer getTempo() {
        return tempo;
    }
}
