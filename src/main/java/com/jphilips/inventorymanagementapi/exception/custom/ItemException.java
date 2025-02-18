package com.jphilips.inventorymanagementapi.exception.custom;

public class ItemException extends RuntimeException {

	private static final long serialVersionUID = -1293651266797012060L;

	public ItemException() {
		super();
	}

	public ItemException(String msg) {
		super(msg);
	}

	public ItemException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItemException(Throwable cause) {
		super(cause);
	}
}
