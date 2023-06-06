using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using WebApplication_pizza.Models;

namespace WebApplication_pizza.Controllers
{
    public class tinnhansController : ApiController
    {
        private qlpizzaEntities14 db = new qlpizzaEntities14();

        // GET: api/tinnhans
        public IQueryable<tinnhan> Gettinnhans()
        {
            return db.tinnhans;
        }

        // GET: api/tinnhans/5
        [ResponseType(typeof(tinnhan))]
        public IHttpActionResult Gettinnhan(string id)
        {
            tinnhan tinnhan = db.tinnhans.Find(id);
            if (tinnhan == null)
            {
                return NotFound();
            }

            return Ok(tinnhan);
        }
        // get thong tin tin nhan
        [HttpGet]
        [Route("tinnhan/{tentg}")]
        public List<tinnhan> getbytinhan2(string tentg)
        {
            List<tinnhan> dsin = db.tinnhans.ToList();
            List<tinnhan> dsout = new List<tinnhan>();
            foreach (tinnhan ls in dsin)
            {
                if (ls.manhan.Equals(tentg)||ls.magui.Equals(tentg))
                    dsout.Add(ls);
            }
            return dsout;
        }
        // PUT: api/tinnhans/5
        [ResponseType(typeof(void))]
        public IHttpActionResult Puttinnhan(string id, tinnhan tinnhan)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tinnhan.matinnhan)
            {
                return BadRequest();
            }

            db.Entry(tinnhan).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!tinnhanExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/tinnhans
        [ResponseType(typeof(tinnhan))]
        public IHttpActionResult Posttinnhan(tinnhan tinnhan)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.tinnhans.Add(tinnhan);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (tinnhanExists(tinnhan.matinnhan))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = tinnhan.matinnhan }, tinnhan);
        }

        // DELETE: api/tinnhans/5
        [ResponseType(typeof(tinnhan))]
        public IHttpActionResult Deletetinnhan(string id)
        {
            tinnhan tinnhan = db.tinnhans.Find(id);
            if (tinnhan == null)
            {
                return NotFound();
            }

            db.tinnhans.Remove(tinnhan);
            db.SaveChanges();

            return Ok(tinnhan);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool tinnhanExists(string id)
        {
            return db.tinnhans.Count(e => e.matinnhan == id) > 0;
        }
    }
}