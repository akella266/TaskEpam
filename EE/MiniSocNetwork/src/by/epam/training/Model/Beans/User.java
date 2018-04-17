package by.epam.training.Model.Beans;

public class User {
    private long id;
    private String firstName;
    private String secondName;
    private String avatar;
    private int age;
    private boolean sex;
    private boolean isFriend;
    private String login;
    private String pass;

    private static String DEFAULT_AVATAR = "http://localhost:8080/uploadFiles/avatars/noavatar.jpg";
    public User() {
    }

    public User(long id, String firstName, String secondName, String avatar, int age, boolean sex, String login, String pass) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.avatar = avatar == null ? DEFAULT_AVATAR : avatar;
        this.age = age;
        this.sex = sex;
        this.login = login;
        this.pass = pass;
    }

    public User(long id, String firstName, String secondName, int age, boolean sex, boolean isFriend) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.avatar = DEFAULT_AVATAR;
        this.age = age;
        this.sex = sex;
        this.isFriend = isFriend;
    }

    public User(long id, String firstName, String secondName, String avatar, int age, boolean sex) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.avatar = avatar == null ? DEFAULT_AVATAR : avatar;
        this.age = age;
        this.sex = sex;
    }

    public User(long id, String firstName, String secondName, String avatar, int age, boolean sex, boolean isFriend) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.avatar = avatar == null ? DEFAULT_AVATAR : avatar;
        this.age = age;
        this.sex = sex;
        this.isFriend = isFriend;
    }

    public User(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public String getSex(){
        return sex ? "Мужской" : "Женский";
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
