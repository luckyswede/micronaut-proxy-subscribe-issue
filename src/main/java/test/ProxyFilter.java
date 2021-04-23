package test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.client.ProxyHttpClient;
import io.micronaut.http.filter.OncePerRequestHttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.runtime.server.EmbeddedServer;
import org.reactivestreams.Publisher;

@Filter("/proxy**")
public class ProxyFilter extends OncePerRequestHttpServerFilter {
    private static final String SCHEME = "http";
    private final ProxyHttpClient proxyHttpClient;
    private final EmbeddedServer embeddedServer;

    public ProxyFilter(ProxyHttpClient proxyHttpClient, EmbeddedServer embeddedServer) {
        this.proxyHttpClient = proxyHttpClient;
        this.embeddedServer = embeddedServer;
    }

    @Override
    protected Publisher<MutableHttpResponse<?>> doFilterOnce(HttpRequest<?> request, ServerFilterChain chain) {
        return proxyHttpClient.proxy(
            request.mutate()
                .uri(builder -> builder
                    .scheme("http")
                    .host(embeddedServer.getHost())
                    .port(embeddedServer.getPort())
                    .replacePath("/")));
    }
}
