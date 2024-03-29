import Link from 'next/link';
import { TagContent } from '../lib/tags';

type Props = {
  tag: string;
};
export default function TagButton({ tag }: Props) {
  return (
    <>
      <Link href={'/posts/tags/[[...slug]]'} as={`/posts/tags/${tag}`}>
        <a>{tag}</a>
      </Link>
      <style jsx>{`
        a {
          display: inline-block;
          border-radius: 3px;
          background-color: rgba(21, 132, 125, 0.2);
          color: hsl(176, 73%, 30%);
          transition: background-color 0.3s ease;
          padding: 0.25em 0.5em;
        }
        a:active,
        a:hover {
          background-color: hsla(176, 73%, 30%, 0.4);
        }
      `}</style>
    </>
  );
}
