package br.com.ciscience.scienceci.view.fragment.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IUserPresenter;
import br.com.ciscience.scienceci.presenter.impl.UserPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.util.ImageUtils;
import br.com.ciscience.scienceci.view.activity.impl.MainActivity;
import br.com.ciscience.scienceci.view.fragment.IFragment;
import br.com.ciscience.scienceci.view.fragment.IUserView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static br.com.ciscience.scienceci.util.ImageUtils.MEDIA_PICTURE;
import static br.com.ciscience.scienceci.util.ImageUtils.REQUEST_CODE_PICTURE_GALLERY;

/**
 * Created by pedrodimoura on 01/09/16.
 */

public class ProfileFragment extends Fragment implements IFragment, IUserView, View.OnClickListener {

    @BindView(R.id.circleImageViewProfile) CircleImageView circleImageViewProfile;
    @BindView(R.id.textInputEditTextName) TextInputEditText textInputEditTextName;
    @BindView(R.id.progressBarMyProfile) ProgressBar progressBarMyProfile;

    private IUserPresenter mIUserPresenter;

    private LoadImageTask mLoadImageTask;
    private View mView;
    private Student mUser;

    private File mFile;

    private static final int IMAGE_VIEW_WIDTH = 300;
    private static final int IMAGE_VIEW_HEIGHT = 300;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(ProfileFragment.this, this.mView);
        this.mIUserPresenter = new UserPresenter(ProfileFragment.this);
        return this.mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void init() {
        this.mUser = this.getSession();
        this.textInputEditTextName.setText(this.mUser.getName());

//        String picturePath = ImageUtils.getLastMedia(getFragmentActivity(), ImageUtils.MEDIA_PICTURE);
//
//        if (picturePath != null)  mFile = new File(picturePath);

        Picasso
                .with(getFragmentActivity())
                .load(Constants.BASE_URL + Constants.DATAFILE_URL + this.mUser.getMyFile().getName())
                .placeholder(R.drawable.bgmaterial)
                .resize(800, 800)
                .onlyScaleDown()
                .into(circleImageViewProfile, new Callback() {

                    @Override
                    public void onSuccess() {
                        progressBarMyProfile.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBarMyProfile.setVisibility(View.GONE);
                        showSnackbarMessage(R.string.error_network_processing, Snackbar.LENGTH_LONG);
                    }
                });

        loadImage();
    }

    private void loadImage() {
        if (this.mFile != null && mFile.exists()) {
            if (mLoadImageTask == null || mLoadImageTask.getStatus() != AsyncTask.Status.RUNNING) {
                mLoadImageTask = new LoadImageTask(getFragmentActivity());
                mLoadImageTask.execute();
            }
        }
    }

    private void onSelectFromGallery(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getFragmentActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        byte[] bitmapData = bos.toByteArray();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mFile, false);
            fileOutputStream.write(bitmapData, 0, bitmapData.length);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        circleImageViewProfile.setImageURI(Uri.parse(mFile.getAbsolutePath()));

        ImageUtils.saveLastMedia(getFragmentActivity(), ImageUtils.MEDIA_PICTURE, mFile.getAbsolutePath());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), mFile);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("media", mFile.getName(), requestFile);

        mUser.getMyFile().setName(mFile.getName());

        mIUserPresenter.changeAvatar(body, mUser.getToken(), mUser);
    }

    private void openCamera() {
        mFile = ImageUtils.newMedia(MEDIA_PICTURE);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
        startActivityForResult(intent, ImageUtils.REQUEST_CODE_PICTURE);
    }

    private void openGallery() {
        mFile = ImageUtils.newMedia(MEDIA_PICTURE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_CODE_PICTURE_GALLERY);
    }

    @Override
    public void setUpListeners() {

    }

    @Override
    public void userLoggedIn() {

    }

    @Override
    public Student getSession() {
        return this.mIUserPresenter.getSession();
    }

    @Override
    public void updateHeaderLayout() {
        ((MainActivity) getFragmentActivity()).updateHeaderLayout();
    }

    @Override
    public void login() {

    }

    @Override
    public void recoveryPassword() {

    }

    @Override
    public void showProgressBar() {
        this.progressBarMyProfile.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        this.progressBarMyProfile.setVisibility(View.GONE);
    }

    @Override
    public void startMainActivity() {

    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void showSnackbarMessage(String message, int duration) {
        Snackbar.make(this.mView, message, duration).show();
    }

    @Override
    public void showSnackbarMessage(int resId, int duration) {
        Snackbar.make(this.mView, resId, duration).show();
    }

    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_PICTURE:
                switch (resultCode) {
                    case AppCompatActivity.RESULT_OK:
                        loadImage();
                        break;
                }
                break;
            case ImageUtils.REQUEST_CODE_PICTURE_GALLERY:
                switch (resultCode) {
                    case AppCompatActivity.RESULT_OK:
                        onSelectFromGallery(data);
                        break;
                }
                break;
        }
    }

    @OnClick({R.id.imageButtonCamera, R.id.imageButtonGallery})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButtonCamera:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getFragmentActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getFragmentActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getFragmentActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    } else {
                        ActivityCompat.requestPermissions(getFragmentActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);
                    }
                } else {
                    openCamera();
                }
                break;
            case R.id.imageButtonGallery:
                openGallery();
                break;
        }
    }

    class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {

        private Context mContext;

        public LoadImageTask(Context context) {
            this.mContext = context;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return ImageUtils.loadImage(mFile, IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                Log.d("TAGM", "Bitmap != null");
                circleImageViewProfile.setImageBitmap(bitmap);
                ImageUtils.saveLastMedia(mContext, MEDIA_PICTURE, mFile.getAbsolutePath());

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), mFile);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("media", mFile.getName(), requestFile);

                mUser.getMyFile().setName(mFile.getName());

                mIUserPresenter.changeAvatar(body, mUser.getToken(), mUser);

            } else {
                Picasso
                        .with(getFragmentActivity())
                        .load(Constants.BASE_URL + Constants.DATAFILE_URL + mUser.getMyFile().getName())
                        .placeholder(R.drawable.bgmaterial)
                        .resize(800, 800)
                        .onlyScaleDown()
                        .into(circleImageViewProfile, new Callback() {

                            @Override
                            public void onSuccess() {
                                progressBarMyProfile.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                progressBarMyProfile.setVisibility(View.GONE);
                                showSnackbarMessage(R.string.error_network_processing, Snackbar.LENGTH_LONG);
                            }
                        });
            }
        }
    }
}
