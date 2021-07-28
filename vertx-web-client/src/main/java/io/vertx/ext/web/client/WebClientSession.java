/*
 * Copyright (c) 2011-2018 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.vertx.ext.web.client;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.client.impl.WebClientSessionAware;
import io.vertx.ext.web.client.spi.CookieStore;

/**
 * An asynchronous sessions aware HTTP / HTTP/2 client called {@code WebClientSession}.
 * <p>
 * This client wraps a {@link WebClient} and makes it session aware adding features to it:
 * <ul>
 *   <li>Per client headers, to be send with every request</li>
 *   <li>Per client cookies, to be send with every request</li>
 *   <li>Automatic storage and sending of cookies received from the server(s)</li>
 * </ul>
 * <p>
 * The client honors the cookies attributes:
 * <ul>
 *  <li>domain</li>
 *  <li>path</li>
 *  <li>secure</li>
 *  <li>max-age and expires</li>
 * </ul>
 * <p/>
 *
 * @author <a href="mailto:tommaso.nolli@gmail.com">Tommaso Nolli</a>
 */
@VertxGen
public interface WebClientSession extends WebClient {

  /**
   * Create a session aware web client using the provided {@code webClient} instance.
   *
   * @param webClient the web client instance
   * @return the created client
   */
  static WebClientSession create(WebClient webClient) {
    return create(webClient, CookieStore.build());
  }

  /**
   * Create a session aware web client using the provided {@code webClient} instance.
   *
   * @param webClient the web client instance
   * @param oAuth2Auth Configured oAuth2Auth provider to be used when {@link #withAuthentication(JsonObject)} used
   * @return the created client
   */
  static WebClientSession create(WebClient webClient, OAuth2Auth oAuth2Auth) {
    return create(webClient, CookieStore.build(), oAuth2Auth);
  }

  /**
   * Create a session aware web client using the provided {@code webClient} instance.
   *
   * @param webClient the web client instance
   * @return the created client
   */
  @GenIgnore
  static WebClientSession create(WebClient webClient, CookieStore cookieStore) {
    return new WebClientSessionAware(webClient, cookieStore, null);
  }

  /**
   * Create a session aware web client using the provided {@code webClient} instance.
   *
   * @param webClient the web client instance
   * @param cookieStore CookieStore to be used for each request
   * @param oAuth2Auth Configured oAuth2Auth provider to be used when {@link #withAuthentication(JsonObject)} used
   * @return the created client
   */
  @GenIgnore
  static WebClientSession create(WebClient webClient, CookieStore cookieStore, OAuth2Auth oAuth2Auth) {
    return new WebClientSessionAware(webClient, cookieStore, oAuth2Auth);
  }

  /**
   * Configure the client to add an HTTP header to every request.
   *
   * @param name the header name
   * @param value the header value
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  @GenIgnore
  WebClientSession addHeader(CharSequence name, CharSequence value);

  /**
   * Configure the client to add an HTTP header to every request.
   *
   * @param name the header name
   * @param value the header value
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  WebClientSession addHeader(String name, String value);

  /**
   * Configure the client to add an HTTP header to every request.
   *
   * @param name the header name
   * @param values the header value
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  @GenIgnore
  WebClientSession addHeader(CharSequence name, Iterable<CharSequence> values);

  /**
   * Configure the client to add an HTTP header to every request.
   *
   * @param name the header name
   * @param values the header value
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  @GenIgnore
  WebClientSession addHeader(String name, Iterable<String> values);

  /**
   * Removes a previously added header.
   *
   * @param name the header name
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  @GenIgnore
  WebClientSession removeHeader(CharSequence name);

  /**
   * Removes a previously added header.
   *
   * @param name the header name
   * @return a reference to this, so the API can be used fluently
   */
  @Fluent
  WebClientSession removeHeader(String name);

  /**
   * Returns this client's {@code CookieStore}
   * <p>
   * All cookies added to this store will be send with every request.
   * The CookieStore honors the domain, path, secure and max-age properties of received cookies
   * and is automatically updated with cookies present in responses received by this client.
   * @return this client's cookie store
   */
  @GenIgnore
  CookieStore cookieStore();

  /**
   * Mark that request should be dispatched with authentication obtained from passed {@code OAuth2Auth} provider
   *
   * @return a reference to this, so the API can be used fluently
   */

  @Fluent
  WebClientSession withCredentials(Credentials credentials);
}
