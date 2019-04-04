package com.mylocumchoice.MyLocumChoicePharmacy.view.notification.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylocumchoice.MyLocumChoicePharmacy.R;
import com.mylocumchoice.MyLocumChoicePharmacy.model.notification.NotificationRes;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification.NotificationPresenter;
import com.mylocumchoice.MyLocumChoicePharmacy.presenter.notification.NotificationVP;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SharedPrefManager;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.SignOutAlert;
import com.mylocumchoice.MyLocumChoicePharmacy.utils.StickyNestedScrollView;
import com.mylocumchoice.MyLocumChoicePharmacy.view.base.BaseFragment;
import com.mylocumchoice.MyLocumChoicePharmacy.view.notification.adapters.NotificationAdapter;
import com.mylocumchoice.MyLocumChoicePharmacy.view.shifts.activities.LandingActivity;

import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotifyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends BaseFragment implements NotificationVP.View{

    /** ButterKnife Code **/
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView title;

   @BindView(R.id.scrollView)
   StickyNestedScrollView scrollView;

    /** ButterKnife Code **/
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private OnFragmentInteractionListener mListener;

    private SignOutAlert signOutAlert;


    private NotificationPresenter presenter;
    private NotificationAdapter notificationAdapter;

    public NotifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        try{
            LandingActivity.ll_clear_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.deleteNotification();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind(this, view);
        //getNotifications();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        try {
            scrollView=view.findViewById(R.id.scrollView);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, 0);
                }
            }, 200);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //title.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotifications();
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
    }

    @Override
    public void onNotificationSuccess(Response<NotificationRes> response) {
        if(response.code() == 200){

            if(response.body().getFinalNotifications() != null) {
                try {
                    if (response.body().getFinalNotifications().size() != 0) {
                        notificationAdapter = new NotificationAdapter("notification", this.getActivity(), response);
                        recyclerView.setAdapter(notificationAdapter);
                        /*try {
                            //recyclerView.scrollToPosition(-1);
                            recyclerView.scrollTo(0,0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                        try {
                            scrollView=view.findViewById(R.id.scrollView);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.scrollTo(0, 0);
                                }
                            }, 200);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try{
                            scrollView.scrollBy(0,0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        recyclerView.setAdapter(null);
                        title.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                /*try {
                    if (response.body().isUnseen_notifications()) {
                        LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.notify_dot));
                    } else {
                        LandingActivity.click_bell.setImageDrawable(getResources().getDrawable(R.drawable.menu_bell_off));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }*/

                try {
                    SharedPrefManager.getInstance(getActivity()).setNotifyDot(response.body().isUnseen_notifications());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else {
            handleAPIErrors(getActivity(), response);
        }
    }

    @Override
    public void onNotificationfailed() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }


    public void getNotifications(){
        presenter = new NotificationPresenter(getActivity(), this);
        presenter.onNotification();

    }


    @Override
    public void onReadNotification(Response<Void> response) {

    }

    @Override
    public void onDeleteNotification(Response<Void> response) {
        if(response.code() == 204){
            getNotifications();
        }
       else {
            handleAPIErrors(getActivity(), response);
        }
    }

    @Override
    public void onDeleteSingleNotification(Response<Void> response) {

    }


}
