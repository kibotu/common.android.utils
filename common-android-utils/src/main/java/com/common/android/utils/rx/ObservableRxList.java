package com.common.android.utils.rx;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Nyaruhodo on 18.08.2016.
 */

public class ObservableRxList<T> {

    protected final List<T> list;
    protected final PublishSubject<RxListItem<T>> subject;

    public ObservableRxList() {
        this.list = new ArrayList<>();
        this.subject = PublishSubject.create();
    }

    /**
     * Note: Swallowing <code>null</code> values.
     */
    public void add(T value) {
        if (value == null)
            return;

        list.add(value);
        subject.onNext(new RxListItem<>(ChangeType.ADD, list.size() - 1, value));
    }

    public void addAll(List<T> items) {
        for (T aList : items) {
            add(aList);
        }
    }

    public void update(T value) {
        for (ListIterator<T> it = list.listIterator(); it.hasNext(); ) {
            final int index = it.nextIndex();
            T next = it.next();
            if (value.equals(next)) {
                it.set(value);
                subject.onNext(new RxListItem<>(ChangeType.UPDATE, index, value));
                return;
            }
        }
    }

    public boolean contains(T dispenser) {
        return list.contains(dispenser);
    }

    public void updateByReference(T value) {
        for (ListIterator<T> it = list.listIterator(); it.hasNext(); ) {
            final int index = it.nextIndex();
            T next = it.next();
            if (value == next) {
                it.set(value);
                subject.onNext(new RxListItem<>(ChangeType.UPDATE, index, value));
                return;
            }
        }
    }

    public void updateAt(int index, T value) {
        for (ListIterator<T> it = list.listIterator(); it.hasNext(); ) {
            final int i = it.nextIndex();
            T next = it.next();
            if (i == index) {
                it.set(value);
                subject.onNext(new RxListItem<>(ChangeType.UPDATE, index, value));
                return;
            }
        }
    }

    public void clear() {
        list.clear();
        subject.onNext(new RxListItem<T>(ChangeType.CLEAR, -1, null));
    }

    public void remove(T value) {
        final int index = list.indexOf(value);
        list.remove(value);
        subject.onNext(new RxListItem<>(ChangeType.REMOVE, index, value));
    }

    public Observable<RxListItem<T>> getObservable() {
        return subject;
    }

    public Observable<T> getCurrentList() {
        return Observable.from(list);
    }

    public enum ChangeType {
        ADD, REMOVE, UPDATE, CLEAR
    }

    public static class RxListItem<T> {
        public ChangeType changeType;
        public int index;
        public T item;

        public RxListItem(ChangeType changeType, int index, T item) {
            this.changeType = changeType;
            this.index = index;
            this.item = item;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RxListItem<?> rxListItem = (RxListItem<?>) o;

            if (index != rxListItem.index) return false;
            if (changeType != rxListItem.changeType) return false;
            return item != null ? item.equals(rxListItem.item) : rxListItem.item == null;

        }

        @Override
        public int hashCode() {
            int result = changeType != null ? changeType.hashCode() : 0;
            result = 31 * result + index;
            result = 31 * result + (item != null ? item.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "RxListItem{" +
                    "changeType=" + changeType +
                    ", index=" + index +
                    ", item=" + item +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObservableRxList<?> that = (ObservableRxList<?>) o;

        if (list != null ? !list.equals(that.list) : that.list != null) return false;
        return subject != null ? subject.equals(that.subject) : that.subject == null;

    }

    @Override
    public int hashCode() {
        int result = list != null ? list.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ObservableRxList{" +
                "list=" + list +
                ", subject=" + subject +
                '}';
    }
}