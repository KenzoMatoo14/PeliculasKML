import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.peliculaskml.Peliculas
import com.example.peliculaskml.R

class PeliAdapter(private val context: Activity, private val arraylist: ArrayList<Peliculas>) :
    ArrayAdapter<Peliculas>(context, R.layout.item, arraylist) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item, parent, false)

        view.findViewById<TextView>(R.id.nombre).text = arraylist[position].nombre
        view.findViewById<TextView>(R.id.itemGenero).text = arraylist[position].genero
        view.findViewById<TextView>(R.id.itemAnio).text = arraylist[position].anio

        return view
    }
}