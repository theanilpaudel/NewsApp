package com.anilpaudel.newsapp

import app.cash.turbine.test
import com.anilpaudel.newsapp.model.DataState
import com.anilpaudel.newsapp.usecase.GetNewsListUC
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Created by Anil Paudel on 26/08/2025.
 */
class GetNewsListUCTest {

    @Test
    fun invoke_repo_emitLoading_SuccessData() = runTest {
        val getNewsListUC = GetNewsListUC(FakeNewsSuccessRepo())
        getNewsListUC.invoke(GetNewsListUC.Params(null)).test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(DataState.Loading::class.java)

            val success = awaitItem()
            assertThat(success).isInstanceOf(DataState.Success::class.java)

            val body = (success as DataState.Success).result
            assertThat(body.articles?.first()?.title).isEqualTo("Test Title")

            awaitComplete()
        }
    }

    @Test
    fun invoke_repo_emitLoading_Error() = runTest {
        val getNewsListUC = GetNewsListUC(FakeNewsErrorRepo())

        getNewsListUC.invoke(GetNewsListUC.Params(null)).test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(DataState.Loading::class.java)

            val error = awaitItem()
            assertThat(error).isInstanceOf(DataState.Error::class.java)

            awaitComplete()
        }
    }
}