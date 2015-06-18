package com.github.mdjc.codesnippets.web;

import java.util.NoSuchElementException;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.mdjc.codesnippets.domain.DuplicateSnippetException;
import com.github.mdjc.codesnippets.domain.DuplicateUserException;

@ControllerAdvice
public class RestAPIControllerAdvice {

	@ResponseBody
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public VndErrors noSuchElementExceptionHandler(NoSuchElementException ex) {
		return new VndErrors("error", ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler({ DuplicateSnippetException.class, DuplicateUserException.class })
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public VndErrors DuplicateSnippetExceptionHandler(DuplicateSnippetException ex) {
		return new VndErrors("error", ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public VndErrors noBadRequestHandler(IllegalArgumentException ex) {
		return new VndErrors("error", ex.getMessage());
	}

	// TODO handle server error

}
