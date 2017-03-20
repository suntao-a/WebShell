package com.sulan.webshell.network;

import android.os.Handler;

import com.jsecode.library.utils.Logger;
import com.sulan.webshell.Const;
import com.sulan.webshell.entities.BaseRequestParam;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import static com.sulan.webshell.Const.METHOD_NAME;
import static com.sulan.webshell.Const.NAME_SPACE;
import static com.sulan.webshell.Const.PARAM_NAME;
import static com.sulan.webshell.Const.REQUEST_TIMEOUT;
import static com.sulan.webshell.Const.RESULT_NAME;
import static com.sulan.webshell.Const.SOAP_ACTION;


/**
 * Created by hohaiuhsx on 14-9-5.
 */
public final class NetWorkHandler {

    private NetWorkHandler() {
    }

    public static Handler mHandler = new Handler();

    public static void soapPost(final String jsonString, final SoapRequestListener listener) {
        soapPost(jsonString, listener, 0);
    }

    public static void soapPost(BaseRequestParam param, SoapRequestListener listener) {
        soapPost(param.toJson(), listener, 0);
    }

    public static void soapPost(final String jsonString, final SoapRequestListener listener, final int tag) {

        new Thread() {
            public void run() {

                // try {
                // Thread.sleep(1000);
                // } catch (InterruptedException e1) {
                // e1.printStackTrace();
                // }

                ExtendedSoapObject soapObject = new ExtendedSoapObject(NAME_SPACE, METHOD_NAME);
                soapObject.addProperty(PARAM_NAME, jsonString);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // 版本
                envelope.bodyOut = soapObject;
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObject);

                HttpTransportSE trans = new HttpTransportSE(Const.getServiceUrl(), REQUEST_TIMEOUT);
                trans.debug = true; // 使用调试功能

                String error = null;
                try {
                    System.out.println("-------- request -------- " + soapObject);
                    trans.call(SOAP_ACTION, envelope);
                    System.out.println("Call Successful!");
                } catch (IOException e) {
                    e.printStackTrace();
                    error = "网络异常，暂时无法访问服务端";
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    error = "服务端数据协议有误";
                } catch (Exception e) {
                    e.printStackTrace();
                    error = "未知异常";
                }

                if (envelope.bodyIn instanceof SoapFault) {
                    SoapFault fault = (SoapFault) envelope.bodyIn;
                    Logger.e(NetWorkHandler.class, fault);
                    error = fault.faultstring;
                } else if (envelope.bodyIn instanceof SoapObject) {
                    SoapObject result = (SoapObject) envelope.bodyIn;
                    Logger.i(NetWorkHandler.class, ">>" + result.toString());
                    final String jsonResponse = result.getPrimitivePropertySafelyAsString(RESULT_NAME);
                    Logger.i(NetWorkHandler.class, "******** response *******" + jsonResponse);

                    if (listener != null) {
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                listener.requestComplete(true, tag, jsonResponse, null);
                            }
                        });
                    }
                    return;
                }

                final String e = error;
                if (listener != null) {
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            listener.requestComplete(false, tag, null, e);
                        }
                    });
                }
            }
        }.start();
    }

    public interface SoapRequestListener {

        void requestComplete(boolean success, int tag, String response, String errorMsg);

    }

}
