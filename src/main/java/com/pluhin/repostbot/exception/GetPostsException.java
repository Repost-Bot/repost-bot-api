package com.pluhin.repostbot.exception;

public class GetPostsException extends RuntimeException {

  public GetPostsException() {
  }

  public GetPostsException(String message) {
    super(message);
  }

  public GetPostsException(Throwable cause) {
    super(cause);
  }
}
