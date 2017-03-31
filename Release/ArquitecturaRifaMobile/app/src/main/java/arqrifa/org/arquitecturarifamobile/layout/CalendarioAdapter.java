package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;

public class CalendarioAdapter extends RecyclerView.Adapter<CalendarioAdapter.CalendarioViewHolder> {

    List<DTReunion> reuniones;
    Context context;

    public CalendarioAdapter(List<DTReunion> reuniones, Context context) {
        this.reuniones = reuniones;
        this.context = context;
    }

    @Override
    public CalendarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reunion_calendario, parent, false);
        return new CalendarioViewHolder(v, context, reuniones);
    }

    @Override
    public void onBindViewHolder(CalendarioViewHolder holder, int position) {
        DTReunion reunion = reuniones.get(position);

        SimpleDateFormat sdfDia = new SimpleDateFormat("dd");
        SimpleDateFormat sdfMes = new SimpleDateFormat("MMMM",  new Locale("es_ES"));
        SimpleDateFormat sdfHora = new SimpleDateFormat("hh:mm");

        CalendarioViewHolder.tvMes.setText(sdfMes.format(reunion.getFecha()));
        CalendarioViewHolder.tvDia.setText(sdfDia.format(reunion.getFecha()));
        CalendarioViewHolder.tvHora.setText(sdfHora.format(reunion.getFecha()));
        CalendarioViewHolder.tvTitulo.setText(reunion.getTitulo());
        CalendarioViewHolder.tvCaracter.setText(reunion.isObligatoria() ? "obligatoria" : "");
        CalendarioViewHolder.tvDescripcion.setText(reunion.getDescripcion());

        if (reunion.getEstado().equals(DTReunion.FINALIZADA)){
            CalendarioViewHolder.llFecha.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return reuniones.size();
    }

    public static class CalendarioViewHolder  extends ViewHolder implements View.OnClickListener {
        List<DTReunion> reuniones = new ArrayList<>();
        Context context;
        private static TextView tvMes;
        private static TextView tvDia;
        private static TextView tvHora;
        private static TextView tvTitulo;
        private static TextView tvCaracter;
        private static TextView tvDescripcion;
        private static LinearLayout llFecha;

        public CalendarioViewHolder(View itemView, Context context, List<DTReunion> reuniones) {
            super(itemView);
            this.reuniones = reuniones;
            this.context = context;
            itemView.setOnClickListener(this);

            tvMes = (TextView)itemView.findViewById(R.id.tvMes);
            tvDia = (TextView)itemView.findViewById(R.id.tvDia);
            tvHora = (TextView)itemView.findViewById(R.id.tvHora);
            tvTitulo = (TextView)itemView.findViewById(R.id.tvTitulo);
            tvCaracter = (TextView)itemView.findViewById(R.id.tvCaracter);
            tvDescripcion = (TextView)itemView.findViewById(R.id.tvDescripcion);
            llFecha = (LinearLayout) itemView.findViewById(R.id.llFecha);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, ReunionActivity.class);

            DTReunion reunion = this.reuniones.get(position);
            intent.putExtra("reunion", reunion);
            context.startActivity(intent);
        }
    }
}
