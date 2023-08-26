import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.radio.explive.R

class dialogo : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder = AlertDialog.Builder(requireContext(), R.style.dialogStyle)
        builder.setTitle("E-XPLOSION")
            .setMessage("Versión 0.1.10\n\n\n\nActualizado por última vez:\n\n10 / AGO / 2023")
            .setPositiveButton("Aceptar") { dialog, which ->

            }


        return builder.create()
    }



}
