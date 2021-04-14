import { AuthorContent } from '../lib/authors';

type Props = {
  author: AuthorContent;
};
export default function Author({ author }: Props) {
  return (
    <>
      <span>{author.name}</span>
      <style jsx>
        {`
          span {
            color: hsl(var(--primary-color) var(--l-30));
          }
        `}
      </style>
    </>
  );
}
