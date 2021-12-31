package com.kwadwomensah.vientest.test

import com.google.common.truth.Truth.assertThat
import com.kwadwomensah.vientest.model.Post
import junit.framework.TestCase
import org.assertj.core.api.Assertions
import org.junit.Test

class ApiTestUtilTest {
    @Test
    fun `check users contain correct domain from api`(){
        val api = ApiTestInterface.create()
        val response = api.getAllPosts().execute()

        val body = response.body()!!.take(2)
        val post = response.body()!![0]
        val code  = response.code()

        Assertions.assertThat(body)
                .hasSize(2)
                .contains(Post(id = post.id, userId = post.userId, title = post.title,
                        body = post.body))
    }
}