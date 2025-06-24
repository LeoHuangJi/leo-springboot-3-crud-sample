package vn.leoo.auth.payload;

public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String id;
    private String email;
    private String name;
    private String imageUrl;
    private String refreshToken;
    
    
    public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public AuthResponse(String accessToken, String id, String email) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
    }
    
    public AuthResponse(String accessToken, String id, String email,String name,String imageUrl,String refreshToken ) {
        this.accessToken = accessToken;
        this.id = id;
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.refreshToken = refreshToken;
    }


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
}
