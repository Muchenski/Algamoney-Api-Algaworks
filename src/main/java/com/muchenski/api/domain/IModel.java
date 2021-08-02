package com.muchenski.api.domain;

import java.io.Serializable;

public interface IModel<ID> extends Serializable {

	public abstract ID getId();

	public abstract void setId(ID id);
}
