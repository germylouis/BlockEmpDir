import com.example.employeedirectory.constants.Constants
import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.RetrofitInstance
import com.example.employeedirectory.data.datasources.api.NetworkHelper.safeApiCall
import com.example.employeedirectory.data.datasources.api.ResultWrapper
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import io.mockk.MockKAnnotations.init
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EmployeeRepoImplTest {

    @MockK
    lateinit var employeeList: ArrayList<Employee>

    private lateinit var employeeRepoImpl: EmployeeRepoImpl

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        init(this)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun getEmployeesSuccessfully() = runTest {
        val instance = RetrofitInstance.getRetrofitInstance(Constants.BASE_EMPLOYEES_URL)?.create(
            EmployeeDataSource::class.java
        )
        if (instance != null) employeeRepoImpl = EmployeeRepoImpl(instance, Constants.EMPLOYEES_JSON_TYPE)
        when (val response = employeeRepoImpl.getEmployees()) {
            is ResultWrapper.Success -> {
                employeeList = response.value?.value?.employees as ArrayList<Employee>
            }
        }
        val actual = employeeList.size
        assert(actual > 0)

    }

    @Test
    fun getGenericErrorWithMalformedData() = runTest {
        val instance = RetrofitInstance.getRetrofitInstance(Constants.BASE_EMPLOYEES_URL)?.create(
            EmployeeDataSource::class.java
        )
        if (instance != null) employeeRepoImpl = EmployeeRepoImpl(instance, Constants.MALFORMED_EMPLOYEES_JSON_TYPE)
        val response = safeApiCall(Dispatchers.Main) { runBlocking { employeeRepoImpl.getEmployees() } as StateFlow<*> }
        assertEquals(ResultWrapper.GenericError(), response)
    }

    @After
    fun tearDown() {
    }
}
