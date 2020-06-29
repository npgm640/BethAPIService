package com.beth.infy.common.persistence;

import java.io.Serializable;

public interface IEntity<P> extends IUniqueIdProvider<P> , Serializable {
}
