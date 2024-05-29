import React from 'react'

const CustomerInfoModal = ({ customerInfo, setCustomerInfo }) => {


    const handleCustomerChange = (e) => {
        const { name, value } = e.target;

        setCustomerInfo(prev => ({...prev, [name]: value}));
    }
        
    return (
        <div className="modal fade" id="customerInfoModal" aria-hidden="true" aria-labelledby="customerInfoModal" tabIndex="-1">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="customerInfoModalToggleLabel2">Customer Information</h1>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <div className='d-flex flex-column gap-2'>
                            <div class="form-group">
                                <label for="customerIdTemp">Customer Id</label>
                                <input 
                                    type="text" 
                                    value={customerInfo?.customerIdTemp} 
                                    name='customerIdTemp'
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerIdTemp"  
                                />
                            </div>
                            <div class="form-group">
                                <label for="customerName">Name of the Customer</label>
                                <input 
                                    type="text" 
                                    name='customerName'
                                    value={customerInfo?.customerName}
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerName" 
                                />
                            </div>
                            <div class="form-group">
                                <label for="customerContact">Person of Contact</label>
                                <input 
                                    type="text" 
                                    name='customerContact'
                                    value={customerInfo?.customerContact}
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerContact" 
                                />
                            </div>
                            <div class="form-group">
                                <label for="customerMobile">Mobile Number</label>
                                <input 
                                    type="text" 
                                    name='customerMobile'
                                    value={customerInfo?.customerMobile}
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerMobile" 
                                />
                            </div>
                            <div class="form-group">
                                <label for="customerAddress">Address</label>
                                <textarea 
                                    value={customerInfo?.customerAddress} 
                                    name='customerAddress'
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerAddress" 
                                />
                            </div>
                            <div class="form-group">
                                <label for="customerCity">City</label>
                                <input 
                                    type='text' 
                                    name='customerCity'
                                    value={customerInfo?.customerCity}
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerCity" 
                                />
                            </div>
                            <div class="form-group">
                                <label for="customerPincode">Pincode</label>
                                <input 
                                    type='text' 
                                    name='customerPincode'
                                    value={customerInfo?.customerPincode}
                                    onChange={handleCustomerChange}
                                    class="form-control" 
                                    id="customerPinCode" 
                                />
                            </div>
                        </div>

                    </div>
                    <div className="modal-footer">
                        <button className="btn btn-primary" data-bs-target="#inputTaskPriorityModal" data-bs-toggle="modal">Back</button>
                        <button className="btn btn-primary" data-bs-target="#taskInfoDetailsModal" data-bs-toggle="modal">Continue</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default CustomerInfoModal