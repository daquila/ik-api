package com.initialkick

class User {

	String name 
	String lastName
	String email
	String password

    static constraints = {
		name (required:true)
		lastName (required:true)
		email (required:true, email:true, unique:true)
		password (required:true)
    }
	
}
