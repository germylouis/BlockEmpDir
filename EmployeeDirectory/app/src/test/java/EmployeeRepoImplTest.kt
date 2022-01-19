import com.example.employeedirectory.constants.Constants
import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.RetrofitInstance
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import io.mockk.MockKAnnotations.init
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test


class EmployeeRepoImplTest {

    @MockK
    lateinit var employeeList: ArrayList<Employee>

    lateinit var employeeRepoImpl: EmployeeRepoImpl

    @Before
    fun setUp() {
        init(this)
        employeeRepoImpl = EmployeeRepoImpl(
            RetrofitInstance.getRetrofitInstance(Constants.EMPLOYEES_URL)?.create(
                EmployeeDataSource::class.java
            )
        )
    }

    @Test
    fun getEmployees() = runBlocking {
        employeeList = employeeRepoImpl.getEmployees()?.value?.employees as ArrayList<Employee>
        val actual = employeeList.size
        val expected = 1
        assertFalse(actual < expected)
    }

    @After
    fun tearDown() {
    }
}
