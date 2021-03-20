package pt.isel.ls.http;

public enum StatusCode {
    Ok(200),
    Created(201),
    BadRequest(400),
    NotFound(404),
    InternalServerError(500);

    private final int code_;

    StatusCode(int code) {
        code_ = code;
    }

    public int getStatus() {
        return code_;
    }

    public static void main(String[] args) {
        int s = StatusCode.BadRequest.getStatus();
        System.out.println(s);
        System.out.println(StatusCode.BadRequest.name());
    }


}

