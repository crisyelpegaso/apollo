/*
 * -\-\-
 * Spotify Apollo Extra
 * --
 * Copyright (C) 2013 - 2015 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */
package com.spotify.apollo.route;

import com.spotify.apollo.Response;

import java.util.Optional;

import okio.ByteString;

/**
 * Defines a possibly open-ended range of versions in which a particular Route should be available.
 * A com.spotify.apollo.route.VersionedRoute with an empty value for {@link #removedIn()} is valid from {@link #validFrom()}
 * inclusive up to the latest version of the API. A com.spotify.apollo.route.VersionedRoute with a value for
 * {@link #removedIn()} is valid in the range [{@link #validFrom()}, {@link #removedIn()}). That is,
 * {@link #removedIn()} defines the first version in which it is no longer valid.
 */
public interface VersionedRoute {
  /**
   * Base route that should be mapped to some versions.
   */
  Route<AsyncHandler<Response<ByteString>>> route();

  /**
   * The first version in which this route is valid
   */
  int validFrom();

  /**
   * The first version in which this route is no longer valid.
   */
  Optional<Integer> removedIn();

  /**
   * Return a com.spotify.apollo.route.VersionedRoute based on the current one, but valid from the specified version.
   */
  VersionedRoute validFrom(int validFrom);

  /**
   * Return a com.spotify.apollo.route.VersionedRoute based on the current one, but removed in the specified version.
   */
  VersionedRoute removedIn(int removedIn);

  /**
   * Create a new com.spotify.apollo.route.VersionedRoute, which is valid from version 0.
   */
  static VersionedRoute of(Route<AsyncHandler<Response<ByteString>>> route) {
    return new AutoValue_VersionedRouteImpl(route, 0, Optional.empty());
  }
}
