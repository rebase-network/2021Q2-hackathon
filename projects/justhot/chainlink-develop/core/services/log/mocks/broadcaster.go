// Code generated by mockery 2.7.4. DO NOT EDIT.

package mocks

import (
	log "github.com/smartcontractkit/chainlink/core/services/log"
	mock "github.com/stretchr/testify/mock"

	models "github.com/smartcontractkit/chainlink/core/store/models"
)

// Broadcaster is an autogenerated mock type for the Broadcaster type
type Broadcaster struct {
	mock.Mock
}

// AddDependents provides a mock function with given fields: n
func (_m *Broadcaster) AddDependents(n int) {
	_m.Called(n)
}

// AwaitDependents provides a mock function with given fields:
func (_m *Broadcaster) AwaitDependents() <-chan struct{} {
	ret := _m.Called()

	var r0 <-chan struct{}
	if rf, ok := ret.Get(0).(func() <-chan struct{}); ok {
		r0 = rf()
	} else {
		if ret.Get(0) != nil {
			r0 = ret.Get(0).(<-chan struct{})
		}
	}

	return r0
}

// DependentReady provides a mock function with given fields:
func (_m *Broadcaster) DependentReady() {
	_m.Called()
}

// IsConnected provides a mock function with given fields:
func (_m *Broadcaster) IsConnected() bool {
	ret := _m.Called()

	var r0 bool
	if rf, ok := ret.Get(0).(func() bool); ok {
		r0 = rf()
	} else {
		r0 = ret.Get(0).(bool)
	}

	return r0
}

// LatestHead provides a mock function with given fields:
func (_m *Broadcaster) LatestHead() *models.Head {
	ret := _m.Called()

	var r0 *models.Head
	if rf, ok := ret.Get(0).(func() *models.Head); ok {
		r0 = rf()
	} else {
		if ret.Get(0) != nil {
			r0 = ret.Get(0).(*models.Head)
		}
	}

	return r0
}

// Register provides a mock function with given fields: listener, opts
func (_m *Broadcaster) Register(listener log.Listener, opts log.ListenerOpts) func() {
	ret := _m.Called(listener, opts)

	var r0 func()
	if rf, ok := ret.Get(0).(func(log.Listener, log.ListenerOpts) func()); ok {
		r0 = rf(listener, opts)
	} else {
		if ret.Get(0) != nil {
			r0 = ret.Get(0).(func())
		}
	}

	return r0
}

// SetLatestHeadFromStorage provides a mock function with given fields: head
func (_m *Broadcaster) SetLatestHeadFromStorage(head *models.Head) {
	_m.Called(head)
}

// Start provides a mock function with given fields:
func (_m *Broadcaster) Start() error {
	ret := _m.Called()

	var r0 error
	if rf, ok := ret.Get(0).(func() error); ok {
		r0 = rf()
	} else {
		r0 = ret.Error(0)
	}

	return r0
}

// Stop provides a mock function with given fields:
func (_m *Broadcaster) Stop() error {
	ret := _m.Called()

	var r0 error
	if rf, ok := ret.Get(0).(func() error); ok {
		r0 = rf()
	} else {
		r0 = ret.Error(0)
	}

	return r0
}

// TrackedAddressesCount provides a mock function with given fields:
func (_m *Broadcaster) TrackedAddressesCount() uint32 {
	ret := _m.Called()

	var r0 uint32
	if rf, ok := ret.Get(0).(func() uint32); ok {
		r0 = rf()
	} else {
		r0 = ret.Get(0).(uint32)
	}

	return r0
}