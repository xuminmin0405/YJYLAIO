package com.ecare.yjylaio.contract;

import com.ecare.yjylaio.base.BasePresenter;
import com.ecare.yjylaio.base.BaseView;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 12/8/21.
 * Email: iminminxu@gmail.com
 */
public interface MainContract {

    interface View extends BaseView {

        void setEnteredTotal(Integer data);
    }

    interface Presenter extends BasePresenter<View> {

        void getEnteredTotal();
    }
}
