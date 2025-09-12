package com.example.app.authentication

import io.fluxzero.sdk.tracking.handling.authentication.RequiresAnyRole
import java.lang.annotation.Documented
import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import java.lang.annotation.ElementType.METHOD
import java.lang.annotation.ElementType.PACKAGE
import java.lang.annotation.ElementType.TYPE

@Target(TYPE, METHOD, PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@RequiresAnyRole
annotation class RequiresRole(vararg val value: Role)