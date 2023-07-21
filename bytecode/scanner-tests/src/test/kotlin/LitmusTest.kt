import com.google.common.truth.Truth.assertThat
import org.approvaltests.Approvals
import org.junit.jupiter.api.Test

class LitmusTest {
  @Test
  fun `test that JUnit and truth are setup`() {
    assertThat(true)
      .isTrue()
  }

  @Test
  internal fun `approvals is setup`() {
    Approvals.verify("Hello, world!")
  }
}
