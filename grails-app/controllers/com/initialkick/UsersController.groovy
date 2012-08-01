package com.initialkick

import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

import com.initialkick.exceptions.BadRequestException
import com.initialkick.exceptions.InternalServerException
import com.initialkick.exceptions.NotFoundException
import com.initialkick.marshallers.UserMarshaller

class UsersController {
	
	def static mutableFields = ["name","last_name","password"]

	def list = {
		render User.list().collect{UserMarshaller.marshall(it)} as JSON
	}
	
	def create = {
		User user = new User()
		def jsonBody = getBody()
		UserMarshaller.bind(user, jsonBody)
		saveDomain(user,HttpServletResponse.SC_CREATED)
	}
	
	def show = {
		User user = User.findById(params.id)
		if (!user){
			throw new NotFoundException()
		}
		response.status = HttpServletResponse.SC_OK
		render UserMarshaller.marshall(user) as JSON
	}
	
	def edit = {
		User user = User.findById(params.id)
		if (!user){
			throw new NotFoundException()
		}
		def jsonBody = getBody()
		validatePutFields(jsonBody)
		UserMarshaller.bind(user, jsonBody)
		saveDomain(user,HttpServletResponse.SC_OK)
	}
	
	def saveDomain = { user, httpCode ->
		if (!user.validate()){
			throw new InternalServerException(user?.errors?.fieldErrors?.toString())
		}
		user.save()
		response.status = httpCode
		render UserMarshaller.marshall(user) as JSON
	}
	
	private def getBody() {
		if ( (request.getContentLength() == 0) || (request.JSON.isEmpty()) ){
			throw new BadRequestException("A body is expected.")
		}
		request.JSON
	}
	
	private def validatePutFields(def jsonBody) {
		// Validate invalid fields
		def domainFields = new DefaultGrailsDomainClass( User.class ).getProperties().collect{it.name}
		def invalidFields = jsonBody.keySet().findAll{!domainFields.contains(it)}
		if (invalidFields.size() > 0){
			throw new BadRequestException("The fields ${invalidFields} are invalid.")
		}
		
		// Validate inmutable fields
		def unMutableFields = jsonBody.keySet().findAll{!mutableFields.contains(it)}
		if (unMutableFields.size() > 0){
			throw new BadRequestException("The fields ${unMutableFields} cannot be modified.")
		}
	}

}
