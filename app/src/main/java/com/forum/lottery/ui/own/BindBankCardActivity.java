package com.forum.lottery.ui.own;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.forum.lottery.R;
import com.forum.lottery.api.UserService;
import com.forum.lottery.entity.ResultData;
import com.forum.lottery.entity.UserVO;
import com.forum.lottery.model.BankVo;
import com.forum.lottery.ui.BaseActivity;
import com.forum.lottery.utils.AccountManager;
import com.forum.lottery.utils.DbManager;

import java.util.ArrayList;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 绑定银行卡
 */

public class BindBankCardActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private Spinner spi_bankCard, spi_openProvince, spi_openCity;
//    private SpinnerAdapter
    private EditText et_bankNo, et_openName, et_password, et_openBank;
    private Button btn_submit;

    private ArrayList<String> mProvinceList;
    private ArrayList<String> mCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_bind_bank_card);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        img_back = findView(R.id.img_back);
        img_back.setOnClickListener(this);
//        spi_bankCard = findView(R.id.spi_bankCard);

        et_openBank = findView(R.id.et_openBank);
        et_bankNo = findView(R.id.et_bankNo);
        et_openName = findView(R.id.et_openName);
        spi_openProvince = findView(R.id.spi_openProvince);
        spi_openCity = findView(R.id.spi_openCity);
        btn_submit = findView(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        et_password = findView(R.id.et_password);

        mProvinceList = new ArrayList<>();
        mProvinceList.addAll(DbManager.getInstance(this).getProvince());
        loadProvince();
    }

    @Override
    protected void initData() {

    }

    private void loadProvince() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, mProvinceList);
        spi_openProvince.setAdapter(adapter);
        spi_openProvince.setSelection(0);
        spi_openProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadCity(mProvinceList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadCity(mProvinceList.get(spi_openProvince.getSelectedItemPosition()));
    }

    private void loadCity(String province) {
        if (spi_openProvince.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            toast("请选择省份");
            return;
        }
        mCityList = DbManager.getInstance(this).getCitis(province);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, mCityList);
        spi_openCity.setAdapter(adapter);
        spi_openCity.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
//            case R.id.spi_openCity:
//                if (spi_openProvince.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
//                    toast("请选择省份");
//                }else{
//                    loadCity(mProvinceList.get(spi_openProvince.getSelectedItemPosition()));
//                }
//                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {

        String openBank = et_openBank.getText().toString().trim();
        if(TextUtils.isEmpty(openBank)){
            toast(R.string.input_open_bank);
            return;
        }
        String bankNo = et_bankNo.getText().toString().trim();
        if(TextUtils.isEmpty(bankNo)){
            toast(R.string.input_bank_no);
            return;
        }
        String openName = et_openName.getText().toString().trim();
        if(TextUtils.isEmpty(openName)){
            toast(R.string.input_open_name);
            return;
        }

        if (spi_openProvince.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            toast("请选择省份");
            return;
        }
        if (spi_openCity.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
            toast("请选择城市");
            return;
        }
        String province = mProvinceList.get(spi_openProvince.getSelectedItemPosition());
        String city = mCityList.get(spi_openCity.getSelectedItemPosition());

        String psw = et_password.getText().toString().trim();
        if(TextUtils.isEmpty(psw)){
            toast(R.string.input_trade_psw);
            return;
        }

        UserVO userVO = AccountManager.getInstance().getUser();
        if(userVO == null){
            toast(R.string.login_prompt);
            return;
        }
        BankVo bankVo = new BankVo();
        bankVo.setBankname(openBank);
        bankVo.setBankno(bankNo);
        bankVo.setTruename(openName);
        bankVo.setProvince(province);
        bankVo.setCity(city);
        bankVo.setTransPwd(psw);
        bankVo.setUsername(userVO.getAccount());
        bankVo.setUserId(Long.valueOf(userVO.getId()));

        showProgressDialog(false);
        createHttp(UserService.class)
                .bindBankCard(bankVo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<ResultData>() {
                    @Override
                    public void onSuccess(ResultData value) {
                        dismissProgressDialog();
                        if(value != null && value.getResult().contains("SUCCESS")){
                            toast("绑定银行卡成功");
                            finish();
                        }else{
                            toast("绑定银行卡失败");
                        }
                    }

                    @Override
                    public void onError(Throwable error) {
                        dismissProgressDialog();
                        toast(R.string.connection_failed);
                    }
                });

    }
}
