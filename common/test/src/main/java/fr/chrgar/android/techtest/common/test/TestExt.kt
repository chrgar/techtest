package fr.chrgar.android.techtest.common.test

import com.google.common.truth.Truth

infix fun <T> T.shouldBe(value: T) = Truth.assertThat(this).isEqualTo(value)
infix fun <T> T.shouldNotBe(value: T) = Truth.assertThat(this).isNotEqualTo(value)

inline fun <reified T> Any?.shouldBeInstanceOf() = Truth.assertThat(this).isInstanceOf(T::class.java)