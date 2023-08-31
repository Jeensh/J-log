package com.jeensh.j_log.api.exception;


/**
 * status -> 404
 */
public class PostNotFound extends JlogException{
    private static final String MESSAGE = "존재하지 않는 글 입니다.";
    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
