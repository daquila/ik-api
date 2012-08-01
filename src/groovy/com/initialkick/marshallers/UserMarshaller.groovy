package com.initialkick.marshallers

import com.initialkick.User

class UserMarshaller {
	
	def static marshall(User user) {
		def map = [:]
		map.id = user.id
		map.name = user.name
		map.last_name = user.lastName
		map.email = user.email
		map.password = user.password
		return map
	}
	
	def static bind(User user, def json) {
		user.name = json.name?:user.name
		user.lastName = json.last_name?:user.lastName
		user.email = json.email?:user.email
		user.password = json.password?:user.password
	}

}
