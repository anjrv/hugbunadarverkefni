package is.hi.feedme.model;

public class CommentDto {

    private long id;
    private String body;
    private Recipe recipe;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Comment getCommentFromDto() {
        Comment comment = new Comment();
        comment.setBody(body);

        return comment;
    }
}