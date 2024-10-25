package com.mikel.calculadora

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mikel.calculadora.databinding.ActivityMainBinding

/**
 * MainActivity es la clase principal de la aplicación de calculadora.
 * Se encarga de manejar los eventos de la UI, procesar las operaciones
 * matemáticas y actualizar la pantalla de resultados.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Variable de enlace para la vista (binding) de ActivityMain
    private lateinit var binding: ActivityMainBinding

    // Primer número para la operación aritmética
    private var firstNumber = 0.0

    // Segundo número para la operación aritmética
    private var secondNumber = 0.0

    // Operación aritmética actual (+, -, *, /)
    private var operation: String? = null

    /**
     * Método onCreate inicializa la actividad y configura el enlace
     * para acceder a los elementos de la UI.
     *
     * @param savedInstanceState estado de la instancia de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa `binding` utilizando el método `inflate`
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Usa `binding.root` en lugar de `R.layout.activity_main`

        // Inicialización de la operación
        operation = null

        // Configura los listeners de clic para cada botón de la calculadora
        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnBorrar.setOnClickListener(this)
        binding.btnSumar.setOnClickListener(this)
        binding.btnMenos.setOnClickListener(this)
        binding.btnMul.setOnClickListener(this)
        binding.btnDiv.setOnClickListener(this)
        binding.btnIgual.setOnClickListener(this)
        binding.btnComa.setOnClickListener(this)
    }

    /**
     * onClick maneja los eventos de clic para los botones de la calculadora.
     *
     * @param view la vista que fue clickeada.
     */
    override fun onClick(view: View?) {
        when (view) {
            binding.btn0 -> onNumberPresses("0")
            binding.btn1 -> onNumberPresses("1")
            binding.btn2 -> onNumberPresses("2")
            binding.btn3 -> onNumberPresses("3")
            binding.btn4 -> onNumberPresses("4")
            binding.btn5 -> onNumberPresses("5")
            binding.btn6 -> onNumberPresses("6")
            binding.btn7 -> onNumberPresses("7")
            binding.btn8 -> onNumberPresses("8")
            binding.btn9 -> onNumberPresses("9")
            binding.btnComa -> onNumberPresses(",")
            binding.btnSumar -> onOperationPresed("+")
            binding.btnMenos -> onOperationPresed("-")
            binding.btnMul -> onOperationPresed("x")
            binding.btnDiv -> onOperationPresed("/")
            binding.btnIgual -> onEqualPressed()
            binding.btnBorrar -> onClearPressed()
        }
    }

    /**
     * onNumberPresses procesa la entrada de números.
     *
     * @param number el número como String que se presionó.
     */
    private fun onNumberPresses(number: String) {
        renderScreen(number)
        checkOperation()
    }

    /**
     * renderScreen actualiza la pantalla de la calculadora con el número ingresado.
     *
     * @param number el número a mostrar en pantalla.
     */
    private fun renderScreen(number: String) {
        val result: String = if (binding.screen.text == "0" && number != ",")
            number
        else
            "${binding.screen.text}$number"

        binding.screen.text = result
    }

    /**
     * checkOperation asigna valores a firstNumber o secondNumber en función de si hay
     * una operación en curso o no.
     */
    private fun checkOperation() {
        if (operation == null)
            firstNumber = binding.screen.text.toString().toDouble()
        else
            secondNumber = binding.screen.text.toString().toDouble()
    }

    /**
     * onOperationPresed establece la operación seleccionada por el usuario.
     *
     * @param operation la operación aritmética seleccionada (+, -, *, /).
     */
    private fun onOperationPresed(operation: String) {
        this.operation = operation
        firstNumber = binding.screen.text.toString().toDouble()

        binding.screen.text = "0"
    }

    /**
     * onEqualPressed realiza la operación aritmética y muestra el resultado en pantalla.
     * Incluye manejo de errores para evitar la división por cero.
     */
    private fun onEqualPressed() {
        // Evita dividir por cero
        if (operation == "/" && secondNumber == 0.0) {
            binding.screen.text = "Error" // Muestra error en la pantalla
            return
        }

        val result = when (operation) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "x" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            else -> 0.0
        }

        // Reset de operación y asignación de resultado
        operation = null
        firstNumber = result
        secondNumber = 0.0

        // Mostrar resultado en pantalla
        binding.screen.text = if (result.toString().endsWith(".0")) {
            result.toInt().toString()
        } else {
            "%.2f".format(result)
        }
    }

    /**
     * onClearPressed restablece la calculadora a su estado inicial.
     */
    private fun onClearPressed() {
        binding.screen.text = "0"
        firstNumber = 0.0
        secondNumber = 0.0
    }
}
