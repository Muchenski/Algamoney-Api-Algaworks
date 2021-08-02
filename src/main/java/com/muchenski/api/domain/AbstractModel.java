package com.muchenski.api.domain;

public abstract class AbstractModel<ID> implements IModel<ID> {

	private static final long serialVersionUID = 1L;

	protected ID id;

	public AbstractModel() {
	}

	public AbstractModel(ID id) {
		this.id = id;
	}

	@Override
	public abstract ID getId();

	@Override
	public abstract void setId(ID id);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		AbstractModel<ID> other = (AbstractModel<ID>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
