package com.initialkick

import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

class HttpErrorsController {

	def badRequest = {
		response.status = HttpServletResponse.SC_BAD_REQUEST
		render createHttpResponse(request.exception.cause.message, "bad_request", response.status, []) as JSON 
	}
	
	def notFound = {
		response.status = HttpServletResponse.SC_NOT_FOUND
		render createHttpResponse("Resource '$request.forwardURI' not found", "not_found", response.status, []) as JSON
	}
	
	def internalServerError = {
		response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
		render createHttpResponse("Oops! Something went wrong...", "internal_error", response.status, request.exception.cause.message) as JSON
	}
	
	private def createHttpResponse(def message, def errorCode, def status, def cause) {
		def resp = [:]
		resp.message = message
		resp.error = errorCode
		resp.status = status
		resp.cause = cause
		return resp
	}
	
}