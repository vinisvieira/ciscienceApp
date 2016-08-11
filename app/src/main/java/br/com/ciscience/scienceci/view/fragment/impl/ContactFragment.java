package br.com.ciscience.scienceci.view.fragment.impl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ciscience.scienceci.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pedrodimoura on 28/07/16.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(ContactFragment.this, this.mView);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.buttonSendEmail, R.id.buttonMakePhoneCall})
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.buttonSendEmail:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.text_view_email_address)});
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_view_contact));
                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, getString(R.string.text_view_choose_email)));
                break;
            case R.id.buttonMakePhoneCall:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.text_view_phone_number))));
                break;
        }
    }
}
