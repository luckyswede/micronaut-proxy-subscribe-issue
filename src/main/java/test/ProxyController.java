package test;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.ProxyHttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import io.reactivex.Single;

@Controller("/")
public class ProxyController {
    private final ProxyHttpClient proxyHttpClient;
    private final EmbeddedServer embeddedServer;


    public ProxyController(ProxyHttpClient proxyHttpClient, EmbeddedServer embeddedServer) {
        this.proxyHttpClient = proxyHttpClient;
        this.embeddedServer = embeddedServer;
    }

    @Get("/proxy")
    public Single<HttpResponse> proxyget(@Nullable @Body Object body, HttpRequest<?> request) {
        return proxy(request);
    }

    @Post("/proxy")
    public Single<HttpResponse> proxypost(@Nullable @Body Object body, HttpRequest<?> request) {
        return proxy(request);
    }

    @Put("/proxy")
    public Single<HttpResponse> proxyput(@Nullable @Body Object body, HttpRequest<?> request) {
        return proxy(request);
    }

    @Delete("/proxy")
    public Single<HttpResponse> proxydelete(@Nullable @Body Object body, HttpRequest<?> request) {
        return proxy(request);
    }

    private Single<HttpResponse> proxy(HttpRequest<?> request) {
        return Single.fromPublisher(proxyHttpClient.proxy(
            request.mutate()
                .uri(builder -> builder
                    .scheme("http")
                    .host(embeddedServer.getHost())
                    .port(embeddedServer.getPort())
                    .replacePath("/"))));
    }

    @Get("/")
    public Single<HttpResponse> get(@Nullable @Body Object body) {
        return Single.just(HttpResponse.ok("hi"));
    }

    @Post("/")
    public Single<HttpResponse> post(@Nullable @Body Object body) {
        return Single.just(HttpResponse.ok("hi"));
    }

    @Put("/")
    public Single<HttpResponse> put(@Nullable @Body Object body) {
        return Single.just(HttpResponse.ok("hi"));
    }

    @Delete("/")
    public Single<HttpResponse> delete(@Nullable @Body Object body) {
        return Single.just(HttpResponse.ok("hi"));
    }
}
