import { format, formatISO } from 'date-fns';

type Props = {
  date: Date;
};
export default function Date({ date }: Props) {
  return (
    <time dateTime={formatISO(date)}>
      <span>{format(date, 'LLLL d, yyyy')}</span>
      <style jsx>
        {`
          span {
            color: hsl(var(--primary-hs) var(--l-30));
          }
        `}
      </style>
    </time>
  );
}
