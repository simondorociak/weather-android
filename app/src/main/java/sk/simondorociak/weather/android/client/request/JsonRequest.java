package sk.simondorociak.weather.android.client.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Helper generic request that serialises JSON source obtained from back-end into T class.
 * It uses @see <a href="http://wiki.fasterxml.com/JacksonHome">Jackson API</a>
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public abstract class JsonRequest<T> extends Request<T> {

    private final Response.Listener<T> listener;
    private final Class<T> clazz;

    public JsonRequest(int method, String url, Response.Listener<T> listener,
                       Response.ErrorListener errorListener, Class<T> clazz) {
        super(method, url, errorListener);
        this.listener = listener;
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T object = new ObjectMapper().readValue(json, clazz);
            return Response.success(object, HttpHeaderParser.parseCacheHeaders(response));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
        catch (JsonParseException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
        catch (IOException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
