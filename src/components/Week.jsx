import React from 'react';
import Day from './Day';

//  Format:
// [
//   0: Week#no
//   1: Monday
//   ...
//   7: Sunday
//       [
//         0: 'DAY',
//         1: 'Training plan'
//         ...
//       ]
// ]
const Week = ({ training }) => (
  <div>
    <h2>{training[0]}</h2>
    <div>
      {training.slice(1).map(day => (
        <Day training={day} />
      ))}
    </div>
  </div>
);

export default Week;
