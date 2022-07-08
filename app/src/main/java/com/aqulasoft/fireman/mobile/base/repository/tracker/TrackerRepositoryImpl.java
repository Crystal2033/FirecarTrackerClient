//package com.aqulasoft.fireman.mobile.base.repository.tracker;
//
//import com.aqulasoft.fireman.mobile.base.repository.Response;
//import com.aqulasoft.fireman.mobile.base.repository.TrackerRepository;
//import com.aqulasoft.fireman.mobile.ui.postlogin.models.VehiclePositionRequest;
//
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.core.Observable;
//import io.reactivex.rxjava3.disposables.Disposable;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//
//public class TrackerRepositoryImpl implements TrackerRepository {
//    TrackerApi api;
//
//    private static <P extends Observable<T>, T extends ResponseBase>
//    Disposable InitApi(P type, Response.ClearSuccess successListener, Response.Error errorListener,
//                       Response.Complete completeListener, Response.Start startListener,
//                       Response.FailedWithMessage failedListener) {
//        Observable<T> f = type.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//
//        return f.subscribe((response) -> onSuccessResponse(response, successListener, failedListener),
//                (throwable) -> {
//                    onError(throwable, errorListener);
//                    onComplete(completeListener);
//                },
//                () -> onComplete(completeListener),
//                (d) -> onStart(startListener));
//    }
//
//    private static <T>
//    void onSuccessResponse(T response, Response.ClearSuccess successListener,
//                           Response.FailedWithMessage failedListener) {
//        if (response.getSuccess()) {
//            if (successListener != null)
//                successListener.onSuccess();
//        } else {
//            if (failedListener != null)
//                failedListener.onFailed(response.getMessage().toString());
//        }
//    }
//
//    @Override
//    public Disposable addLocationsPack(VehiclePositionRequest request,
//                                       Response.Start startListener,
//                                       Response.Success<Boolean> successListener,
//                                       Response.FailedWithMessage failedListener,
//                                       Response.Error errorListener,
//                                       Response.Complete completeListener) {
//        Observable<VehiclePositionRequest> disposable = api.addLocationsPack(request);
//        return InitApi(disposable, startListener, successListener, failedListener, errorListener, completeListener);
//    }
//}
