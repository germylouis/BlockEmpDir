import com.example.employeedirectory.constants.Constants
import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.RetrofitInstance
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import com.example.employeedirectory.viewmodels.LatestEmployeesUiState
import io.mockk.MockKAnnotations.init
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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

    @MockK
    lateinit var state: MutableStateFlow<LatestEmployeesUiState>


    private lateinit var employeeRepoImpl: EmployeeRepoImpl

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        init(this)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        val instance = RetrofitInstance.getRetrofitInstance(Constants.BASE_EMPLOYEES_URL)?.create(
            EmployeeDataSource::class.java
        )
        if (instance != null) employeeRepoImpl = EmployeeRepoImpl(instance, Constants.EMPLOYEES_JSON_TYPE)

        runTest {
            state = employeeRepoImpl.getEmployees()
            assert(state.value is LatestEmployeesUiState.Success)
        }
    }

    @Test
    fun `when lambda returns successfully then it should emit the result as error`() {
        val instance = RetrofitInstance.getRetrofitInstance(Constants.BASE_EMPLOYEES_URL)?.create(
            EmployeeDataSource::class.java
        )
        if (instance != null) employeeRepoImpl = EmployeeRepoImpl(instance, Constants.EMPTY_EMPLOYEES_JSON_TYPE)

        runTest {
            state = employeeRepoImpl.getEmployees()
            assert(state.value is LatestEmployeesUiState.Error)
        }
    }

//    @Test
//    fun `when lambda throws HttpException then it should emit the result as GenericError`() {
//        val instance = RetrofitInstance.getRetrofitInstance(Constants.BASE_EMPLOYEES_URL)?.create(
//            EmployeeDataSource::class.java
//        )
//        if (instance != null) employeeRepoImpl = EmployeeRepoImpl(instance, "")
//        runTest {
//            val result = safeApiCall(dispatcher) {
//                runBlocking { employeeRepoImpl.getEmployees() }
//
//            }
//            assertEquals(  )
//        }
//    }

    @After
    fun tearDown() {
    }
}
