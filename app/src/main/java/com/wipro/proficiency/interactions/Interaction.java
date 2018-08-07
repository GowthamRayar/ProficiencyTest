package com.wipro.proficiency.interactions;

import rx.Observable;

public interface Interaction<T> {
    Observable<T> execute();
}
