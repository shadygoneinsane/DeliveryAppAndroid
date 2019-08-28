package com.deliveryapp.testing

@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class AllOpen

@AllOpen
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting