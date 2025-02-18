package com.jphilips.inventorymanagementapi.exception.custom;

public class ItemNotFoundException extends ItemException{

	private static final long serialVersionUID = 9062335302058104409L;
	
	public ItemNotFoundException() {
		super("Item Not Found");
	}

	public ItemNotFoundException(String msg) {
		super(msg);
	}

	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ItemNotFoundException(Throwable cause) {
		super(cause);
	}

}
