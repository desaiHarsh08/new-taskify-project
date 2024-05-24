import React from 'react'

const FilterTabs = ({ handleTabClick, setSelectedTab, tabArr, setTabArr }) => {
  return (
    <div className='my-3 w-100 overflow-auto'>
        <ul className='d-flex gap-5 border-bottom'  style={{minWidth: "1128px"}}>
            {tabArr.map((tab, index) => (
                <li onClick={() => handleTabClick(index)} className={`list-group-item d-flex gap-2 border-bottom ${tab.isSelected ? 'border-primary text-primary' : 'border-transparent'}`} style={{ cursor: "pointer"}}>
                    <p>{tab.tabLabel}</p>
                    <p className='badge text-bg-primary'>{tab.totalTask}</p>
                </li>
            ))}
        </ul>
    </div>
  )
}

export default FilterTabs