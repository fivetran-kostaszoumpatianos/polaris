/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.polaris.core.storage.cache;

import java.util.Objects;
import java.util.Set;
import org.apache.polaris.core.entity.PolarisEntity;
import org.apache.polaris.core.entity.PolarisEntityConstants;

public class StorageCredentialCacheKey {

  private final String realmId;
  private final long catalogId;

  /** The serialized string of the storage config. */
  private final String storageConfigSerializedStr;

  /**
   * The entity id is passed to be used to fetch subscoped creds, but is not used to do hash/equals
   * as part of the cache key.
   */
  private final long entityId;

  private final boolean allowedListAction;
  private final Set<String> allowedReadLocations;

  private final Set<String> allowedWriteLocations;

  public StorageCredentialCacheKey(
      String realmId,
      PolarisEntity entity,
      boolean allowedListAction,
      Set<String> allowedReadLocations,
      Set<String> allowedWriteLocations) {
    this.realmId = realmId;
    this.catalogId = entity.getCatalogId();
    this.storageConfigSerializedStr =
        entity
            .getInternalPropertiesAsMap()
            .get(PolarisEntityConstants.getStorageConfigInfoPropertyName());
    this.entityId = entity.getId();
    this.allowedListAction = allowedListAction;
    this.allowedReadLocations = allowedReadLocations;
    this.allowedWriteLocations = allowedWriteLocations;
  }

  public String getRealmId() {
    return realmId;
  }

  public long getCatalogId() {
    return catalogId;
  }

  public String getStorageConfigSerializedStr() {
    return storageConfigSerializedStr;
  }

  public long getEntityId() {
    return entityId;
  }

  public boolean isAllowedListAction() {
    return allowedListAction;
  }

  public Set<String> getAllowedReadLocations() {
    return allowedReadLocations;
  }

  public Set<String> getAllowedWriteLocations() {
    return allowedWriteLocations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StorageCredentialCacheKey cacheKey = (StorageCredentialCacheKey) o;
    return Objects.equals(realmId, cacheKey.getRealmId())
        && catalogId == cacheKey.getCatalogId()
        && Objects.equals(storageConfigSerializedStr, cacheKey.getStorageConfigSerializedStr())
        && allowedListAction == cacheKey.allowedListAction
        && Objects.equals(allowedReadLocations, cacheKey.allowedReadLocations)
        && Objects.equals(allowedWriteLocations, cacheKey.allowedWriteLocations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        realmId,
        catalogId,
        storageConfigSerializedStr,
        allowedListAction,
        allowedReadLocations,
        allowedWriteLocations);
  }

  @Override
  public String toString() {
    return "StorageCredentialCacheKey{"
        + "realmId="
        + realmId
        + ", catalogId="
        + catalogId
        + ", storageConfigSerializedStr='"
        + storageConfigSerializedStr
        + '\''
        + ", entityId="
        + entityId
        + ", allowedListAction="
        + allowedListAction
        + ", allowedReadLocations="
        + allowedReadLocations
        + ", allowedWriteLocations="
        + allowedWriteLocations
        + '}';
  }
}
