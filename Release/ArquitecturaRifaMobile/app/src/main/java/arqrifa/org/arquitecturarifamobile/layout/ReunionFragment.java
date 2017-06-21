package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;

public class ReunionFragment extends Fragment {
    TextView tvFecha, tvTitulo, tvDescripcion, tvDuracion, tvLugar, tvCaracter, tvEstado, tvObservaciones, tvTituloEncuesta;
    LinearLayout llTemas, llResoluciones;
    CardView cvResoluciones, cvEncuesta;

    private DTReunion reunion;

    private OnFragmentInteractionListener mListener;

    public ReunionFragment() {
    }


    public static ReunionFragment newInstance(DTReunion reunion) {
        ReunionFragment fragment = new ReunionFragment();
        Bundle args = new Bundle();
        args.putSerializable("reunion", reunion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reunion = (DTReunion) getArguments().getSerializable("reunion");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reunion, container, false);
        tvTitulo = (TextView)view.findViewById(R.id.tvTitulo);
        tvFecha = (TextView)view.findViewById(R.id.tvFecha);
        tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
        tvDuracion = (TextView) view.findViewById(R.id.tvDuracion);
        tvLugar = (TextView) view.findViewById(R.id.tvLugar);
        tvCaracter = (TextView) view.findViewById(R.id.tvCaracter);
        tvEstado = (TextView) view.findViewById(R.id.tvEstado);
        cvResoluciones = (CardView) view.findViewById(R.id.cvResoluciones);
        llResoluciones = (LinearLayout) view.findViewById(R.id.llResoluciones);
        llTemas = (LinearLayout) view.findViewById(R.id.llTemas);
        cvEncuesta = (CardView) view.findViewById(R.id.cvEncuesta);
        tvObservaciones = (TextView) view.findViewById(R.id.tvObservaciones);
        tvTituloEncuesta = (TextView) view.findViewById(R.id.tvTituloEncuesta);
        setReunion();
        return view;
    }

    public void setReunion(){
        tvFecha.setText(new SimpleDateFormat("dd MMMM, yyyy HH:mm ", new Locale("es_ES")).format(reunion.getFecha()));
        tvTitulo.setText(reunion.getTitulo());
        tvDescripcion.setText(reunion.getDescripcion());
        tvDuracion.setText("Duración estimada: " + reunion.getDuracion());
        tvLugar.setText("Lugar: " + reunion.getLugar());
        tvCaracter.setText("Obligatoria: " + (reunion.isObligatoria()? "Sí" : "No"));
        tvEstado.setText("Estado: " + reunion.getEstado());

        TextView tvTema;
        for (String tema : reunion.getTemas()){
            tvTema = new TextView(getActivity());
            tvTema.setText(tema);
            tvTema.setTextSize(11);
            llTemas.addView(tvTema);
        }

        if(reunion.getEstado().equals(DTReunion.FINALIZADA)){

            TextView tvResolucion;
            for(String resolucion : reunion.getResoluciones()){
                tvResolucion = new TextView(getActivity());
                tvResolucion.setText(resolucion);
                tvResolucion.setTextSize(11);
                llResoluciones.addView(tvResolucion);
            }

            tvObservaciones.setText(reunion.getObservaciones());

            cvResoluciones.setVisibility(View.VISIBLE);
        } else {
            cvResoluciones.setVisibility(View.GONE);
        }

        if(reunion.getEncuesta() != null){
            cvEncuesta.setVisibility(View.VISIBLE);
            cvEncuesta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if(reunion.getEncuesta().isHabilitada()){
                        intent = new Intent(getActivity(), CuestionarioActivity.class);
                    }
                    else {
                        intent = new Intent(getActivity(), EncuestaActivity.class);
                    }

                    intent.putExtra("reunion", reunion);
                    getActivity().startActivity(intent);
                }
            });
            tvTituloEncuesta.setText(reunion.getEncuesta().getTitulo());
        }
        else {
            cvEncuesta.setVisibility(View.GONE);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
