abstract class BaseEntity {
    protected long id;
    protected long version;

    protected BaseEntity()
    {
        id = 0;
        version = 0;
    }

    protected BaseEntity(long id, long version)
    {
        this.id = id;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}

class User extends BaseEntity {

    protected String name;

    protected User()
    {
        super(0,0);
        name = "Unknown";
    }

    protected User(long id, long version, String name)
    {
        super(id, version);
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Visit extends BaseEntity {
    protected User user;
    protected WebSite site;

    protected Visit()
    {
        super(0, 0);
        this.user = new User();
        this.site = new WebSite();
    }
    protected Visit(long id, long version, User user, WebSite site)
    {
        super(id, version);
        this.user = user;
        this.site = site;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WebSite getSite() {
        return site;
    }

    public void setSite(WebSite site) {
        this.site = site;
    }
}

class WebSite extends BaseEntity {
    protected String url;

    protected WebSite()
    {
        super(0, 0);
        url = "blank";
    }
    protected WebSite(long id, long version, String url)
    {
        super(id, version);
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}